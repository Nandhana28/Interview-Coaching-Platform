-- Assuming the 'users' table already exists based on your backend models (User.java).
-- If not, you can add it, but focusing on mock test module as requested.

-- Enum for difficulty levels
CREATE TYPE difficulty_level AS ENUM ('easy', 'medium', 'hard');

-- Table for subjects/topics
CREATE TABLE subjects (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    description TEXT,  -- Optional description for the subject
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table for questions in the question bank
CREATE TABLE questions (
    id SERIAL PRIMARY KEY,
    subject_id INTEGER REFERENCES subjects(id) ON DELETE CASCADE,
    difficulty difficulty_level NOT NULL,
    question_text TEXT NOT NULL,
    question_type VARCHAR(50) DEFAULT 'mcq',  -- e.g., 'mcq', 'short_answer', 'coding'
    options JSONB,  -- For MCQ: e.g., {"A": "Option A", "B": "Option B", "C": "Option C", "D": "Option D"}
    correct_answer TEXT NOT NULL,  -- For MCQ: 'A', for others: the answer text
    explanation TEXT,  -- Detailed explanation for learning
    tags VARCHAR(255)[],  -- Array of tags for filtering, e.g., {'sql', 'normalization'}
    times_asked INTEGER DEFAULT 0,  -- Track how many times this question has been used in tests
    average_score DECIMAL(5,2) DEFAULT 0.00,  -- Average user score on this question
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table for courses (since mock tests are like courses/journeys)
CREATE TABLE courses (
    id SERIAL PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    description TEXT,
    level difficulty_level,  -- Overall course difficulty
    estimated_duration_hours INTEGER,  -- Estimated time to complete
    prerequisites TEXT,  -- Comma-separated prerequisite course IDs or subjects
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table for mock tests (templates/journeys within courses)
CREATE TABLE mock_tests (
    id SERIAL PRIMARY KEY,
    course_id INTEGER REFERENCES courses(id) ON DELETE SET NULL,
    name VARCHAR(200) NOT NULL,
    description TEXT,
    duration_minutes INTEGER DEFAULT 60,  -- Time limit for the test
    total_questions INTEGER NOT NULL,
    passing_score DECIMAL(5,2) DEFAULT 70.00,  -- Minimum score to pass
    max_attempts INTEGER DEFAULT 3,  -- Max attempts per user
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Junction table for mock test questions (many-to-many with order)
CREATE TABLE mock_test_questions (
    mock_test_id INTEGER REFERENCES mock_tests(id) ON DELETE CASCADE,
    question_id INTEGER REFERENCES questions(id) ON DELETE CASCADE,
    question_order INTEGER NOT NULL,  -- Order in the test
    points INTEGER DEFAULT 1,  -- Points for this question in the test
    PRIMARY KEY (mock_test_id, question_id)
);

-- Table for user attempts on mock tests (tracking progress)
CREATE TABLE user_mock_test_attempts (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,  -- Assuming users table exists
    mock_test_id INTEGER REFERENCES mock_tests(id) ON DELETE CASCADE,
    attempt_number INTEGER DEFAULT 1,
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    score DECIMAL(5,2) DEFAULT 0.00,
    completed BOOLEAN DEFAULT FALSE,
    feedback TEXT,  -- AI-generated or manual feedback
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table for user answers in attempts (detailed tracking for analysis)
CREATE TABLE user_answers (
    id SERIAL PRIMARY KEY,
    attempt_id INTEGER REFERENCES user_mock_test_attempts(id) ON DELETE CASCADE,
    question_id INTEGER REFERENCES questions(id) ON DELETE CASCADE,
    user_response TEXT,  -- User's answer (option or text)
    is_correct BOOLEAN,
    time_taken_seconds INTEGER,  -- Time spent on this question
    answered_at TIMESTAMP
);

-- Gamification tables

-- Table for badges
CREATE TABLE badges (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    icon_url VARCHAR(255),  -- URL to badge icon
    points_required INTEGER,  -- Points needed to unlock
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table for user badges (many-to-many)
CREATE TABLE user_badges (
    user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
    badge_id INTEGER REFERENCES badges(id) ON DELETE CASCADE,
    awarded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, badge_id)
);

-- Table for user points (gamification tracking)
CREATE TABLE user_points (
    user_id INTEGER PRIMARY KEY REFERENCES users(id) ON DELETE CASCADE,
    total_points INTEGER DEFAULT 0,
    level INTEGER DEFAULT 1,  -- User level based on points
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table for leaderboards (gamified rankings, can be views but here's a table for storage)
CREATE TABLE leaderboards (
    id SERIAL PRIMARY KEY,
    type VARCHAR(50) NOT NULL,  -- e.g., 'global', 'course_specific'
    course_id INTEGER REFERENCES courses(id) ON DELETE SET NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Junction for leaderboard entries
CREATE TABLE leaderboard_entries (
    leaderboard_id INTEGER REFERENCES leaderboards(id) ON DELETE CASCADE,
    user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
    rank INTEGER NOT NULL,
    score INTEGER NOT NULL,
    PRIMARY KEY (leaderboard_id, user_id)
);

-- Indexes for performance
CREATE INDEX idx_questions_subject ON questions(subject_id);
CREATE INDEX idx_questions_difficulty ON questions(difficulty);
CREATE INDEX idx_mock_test_questions_mock_test ON mock_test_questions(mock_test_id);
CREATE INDEX idx_user_attempts_user ON user_mock_test_attempts(user_id);
CREATE INDEX idx_user_attempts_test ON user_mock_test_attempts(mock_test_id);
CREATE INDEX idx_user_answers_attempt ON user_answers(attempt_id);

-- Triggers example: Update updated_at
CREATE OR REPLACE FUNCTION update_timestamp()
RETURNS TRIGGER AS $$
BEGIN
   NEW.updated_at = CURRENT_TIMESTAMP;
   RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_subjects_timestamp
BEFORE UPDATE ON subjects
FOR EACH ROW EXECUTE PROCEDURE update_timestamp();

-- Similar triggers can be added for other tables.

-- Now, populate with data
-- First, insert subjects (I added 'Computer Networks' to make 10 subjects for 50 questions, as 9x5=45, but you requested 50)

INSERT INTO subjects (name, description) VALUES
('DBMS', 'Database Management Systems'),
('OOPS', 'Object-Oriented Programming'),
('OS', 'Operating Systems'),
('Linear Algebra', 'Mathematical concepts in linear algebra'),
('Statistics', 'Statistical methods and analysis'),
('Probability', 'Probability theory'),
('Stochastic Models', 'Stochastic processes and models'),
('Design and Analysis of Algorithms', 'Algorithm design techniques and analysis'),
('DSA', 'Data Structures and Algorithms'),
('Computer Networks', 'Computer networking concepts');

-- Now, insert 50 questions (5 per subject, mixed difficulties: roughly 2 easy, 2 medium, 1 hard per subject)
-- Questions are MCQ with options as JSONB, correct_answer as the key (A/B/C/D)

-- DBMS Questions
INSERT INTO questions (subject_id, difficulty, question_text, options, correct_answer, explanation) VALUES
(1, 'easy', 'What does SQL stand for?', '{"A": "Structured Query Language", "B": "Simple Query Language", "C": "Standard Query Language", "D": "Sequential Query Language"}', 'A', 'SQL is a standard language for managing relational databases.'),
(1, 'easy', 'What is a primary key?', '{"A": "A unique identifier for a record", "B": "A foreign key reference", "C": "An index", "D": "A data type"}', 'A', 'Primary key uniquely identifies each record in a table.'),
(1, 'medium', 'What is normalization in DBMS?', '{"A": "Process to eliminate data redundancy", "B": "Adding more data", "C": "Deleting data", "D": "Encrypting data"}', 'A', 'Normalization organizes data to reduce redundancy and improve integrity.'),
(1, 'medium', 'What is a JOIN in SQL?', '{"A": "Combining rows from two or more tables", "B": "Splitting tables", "C": "Deleting rows", "D": "Updating rows"}', 'A', 'JOIN combines data from multiple tables based on related columns.'),
(1, 'hard', 'Explain ACID properties in transactions.', '{"A": "Atomicity, Consistency, Isolation, Durability", "B": "Availability, Consistency, Integrity, Durability", "C": "Atomicity, Confidentiality, Isolation, Durability", "D": "Availability, Confidentiality, Integrity, Durability"}', 'A', 'ACID ensures reliable transaction processing in databases.');

-- OOPS Questions
INSERT INTO questions (subject_id, difficulty, question_text, options, correct_answer, explanation) VALUES
(2, 'easy', 'What is encapsulation in OOPS?', '{"A": "Wrapping data and methods together", "B": "Inheriting properties", "C": "Overloading methods", "D": "Abstracting classes"}', 'A', 'Encapsulation binds data and functions into a single unit.'),
(2, 'easy', 'What is inheritance?', '{"A": "Acquiring properties from parent class", "B": "Hiding data", "C": "Overriding methods", "D": "Polymorphism"}', 'A', 'Inheritance allows a class to inherit features from another class.'),
(2, 'medium', 'What is polymorphism?', '{"A": "Many forms of a method", "B": "Single form", "C": "Data hiding", "D": "Class abstraction"}', 'A', 'Polymorphism allows methods to do different things based on object.'),
(2, 'medium', 'What is an abstract class?', '{"A": "Class that cannot be instantiated", "B": "Class with no methods", "C": "Final class", "D": "Static class"}', 'A', 'Abstract classes provide a base for subclasses but cannot be created directly.'),
(2, 'hard', 'Explain the difference between composition and aggregation.', '{"A": "Composition is strong ownership, aggregation is weak", "B": "Both are the same", "C": "Aggregation is strong, composition is weak", "D": "Neither involves ownership"}', 'A', 'Composition implies the child cannot exist without parent, aggregation allows independent existence.');

-- OS Questions
INSERT INTO questions (subject_id, difficulty, question_text, options, correct_answer, explanation) VALUES
(3, 'easy', 'What is a process in OS?', '{"A": "Executing program", "B": "Hardware component", "C": "File system", "D": "Network protocol"}', 'A', 'A process is a program in execution.'),
(3, 'easy', 'What is multitasking?', '{"A": "Running multiple processes simultaneously", "B": "Single process execution", "C": "Hardware management", "D": "File storage"}', 'A', 'Multitasking allows multiple tasks to run concurrently.'),
(3, 'medium', 'What is deadlock?', '{"A": "Processes waiting for each other indefinitely", "B": "Process termination", "C": "High CPU usage", "D": "Memory overflow"}', 'A', 'Deadlock occurs when processes hold resources and wait for others.'),
(3, 'medium', 'What is paging in memory management?', '{"A": "Dividing memory into pages", "B": "Segmenting processes", "C": "Caching data", "D": "Encrypting memory"}', 'A', 'Paging is a memory management scheme that eliminates fragmentation.'),
(3, 'hard', 'Explain Banker''s algorithm.', '{"A": "Deadlock avoidance algorithm", "B": "Scheduling algorithm", "C": "Memory allocation", "D": "File system check"}', 'A', 'Banker''s algorithm simulates resource allocation to avoid deadlocks.');

-- Linear Algebra Questions
INSERT INTO questions (subject_id, difficulty, question_text, options, correct_answer, explanation) VALUES
(4, 'easy', 'What is a matrix?', '{"A": "Rectangular array of numbers", "B": "Single number", "C": "Vector", "D": "Function"}', 'A', 'A matrix is a 2D array used in linear transformations.'),
(4, 'easy', 'What is the determinant of a 1x1 matrix [a]?', '{"A": "a", "B": "0", "C": "1", "D": "Undefined"}', 'A', 'Determinant of [a] is a itself.'),
(4, 'medium', 'What is an eigenvector?', '{"A": "Vector that doesn''t change direction under transformation", "B": "Zero vector", "C": "Scalar multiple", "D": "Orthogonal vector"}', 'A', 'Eigenvectors are scaled but not rotated by the matrix.'),
(4, 'medium', 'What is matrix multiplication?', '{"A": "Row-by-column dot product", "B": "Element-wise multiplication", "C": "Addition of matrices", "D": "Subtraction"}', 'A', 'Multiplication combines rows and columns via dot products.'),
(4, 'hard', 'What is the rank of a matrix?', '{"A": "Number of linearly independent rows/columns", "B": "Total rows", "C": "Total columns", "D": "Determinant value"}', 'A', 'Rank indicates the dimension of the column space.');

-- Statistics Questions
INSERT INTO questions (subject_id, difficulty, question_text, options, correct_answer, explanation) VALUES
(5, 'easy', 'What is the mean?', '{"A": "Average of data", "B": "Middle value", "C": "Most frequent", "D": "Range"}', 'A', 'Mean is the sum divided by count.'),
(5, 'easy', 'What is variance?', '{"A": "Measure of data spread", "B": "Average", "C": "Median", "D": "Mode"}', 'A', 'Variance quantifies dispersion from the mean.'),
(5, 'medium', 'What is a p-value?', '{"A": "Probability of observing data given null hypothesis", "B": "Confidence level", "C": "Sample size", "D": "Effect size"}', 'A', 'P-value helps in hypothesis testing.'),
(5, 'medium', 'What is regression analysis?', '{"A": "Modeling relationship between variables", "B": "Clustering data", "C": "Classifying data", "D": "Dimensionality reduction"}', 'A', 'Regression predicts dependent variable from independents.'),
(5, 'hard', 'Explain Bayes'' theorem in statistics.', '{"A": "P(A|B) = P(B|A)P(A)/P(B)", "B": "P(A and B) = P(A)P(B)", "C": "P(A or B) = P(A) + P(B)", "D": "P(A) = 1 - P(not A)"}', 'A', 'Bayes'' theorem updates probabilities based on new evidence.');

-- Probability Questions
INSERT INTO questions (subject_id, difficulty, question_text, options, correct_answer, explanation) VALUES
(6, 'easy', 'What is probability?', '{"A": "Likelihood of an event", "B": "Certainty", "C": "Impossibility", "D": "Frequency"}', 'A', 'Probability measures how likely an event is to occur.'),
(6, 'easy', 'What is a sample space?', '{"A": "All possible outcomes", "B": "Single outcome", "C": "Favorable outcomes", "D": "Null set"}', 'A', 'Sample space is the set of all possible results.'),
(6, 'medium', 'What is conditional probability?', '{"A": "Probability given another event", "B": "Independent probability", "C": "Joint probability", "D": "Marginal probability"}', 'A', 'It''s P(A|B) = P(A and B)/P(B).'),
(6, 'medium', 'What is binomial distribution?', '{"A": "Fixed trials with two outcomes", "B": "Continuous distribution", "C": "Poisson events", "D": "Normal curve"}', 'A', 'Binomial models number of successes in trials.'),
(6, 'hard', 'What is the law of large numbers?', '{"A": "Sample average converges to expected value", "B": "Events are independent", "C": "Probabilities add to 1", "D": "Variance decreases"}', 'A', 'As trials increase, empirical mean approaches theoretical mean.');

-- Stochastic Models Questions
INSERT INTO questions (subject_id, difficulty, question_text, options, correct_answer, explanation) VALUES
(7, 'easy', 'What is a stochastic process?', '{"A": "Random process over time", "B": "Deterministic process", "C": "Static model", "D": "Fixed outcome"}', 'A', 'Stochastic processes involve randomness.'),
(7, 'easy', 'What is a Markov chain?', '{"A": "Process where next state depends only on current", "B": "Independent states", "C": "Continuous time", "D": "Deterministic chain"}', 'A', 'Markov property assumes memoryless states.'),
(7, 'medium', 'What is Poisson process?', '{"A": "Counting rare events over time", "B": "Continuous distribution", "C": "Binomial trials", "D": "Normal approximation"}', 'A', 'Poisson models event counts in fixed intervals.'),
(7, 'medium', 'What is Brownian motion?', '{"A": "Continuous stochastic process", "B": "Discrete steps", "C": "Fixed path", "D": "Deterministic motion"}', 'A', 'It''s a random walk in continuous time.'),
(7, 'hard', 'Explain martingale in stochastic models.', '{"A": "Process where expected future value equals current", "B": "Increasing process", "C": "Decreasing process", "D": "Periodic process"}', 'A', 'Martingales have fair game property.');

-- Design and Analysis of Algorithms Questions
INSERT INTO questions (subject_id, difficulty, question_text, options, correct_answer, explanation) VALUES
(8, 'easy', 'What is time complexity?', '{"A": "Measure of execution time", "B": "Space used", "C": "Number of operations", "D": "Input size"}', 'A', 'Time complexity analyzes algorithm efficiency.'),
(8, 'easy', 'What is Big O notation?', '{"A": "Upper bound on complexity", "B": "Lower bound", "C": "Exact bound", "D": "Average bound"}', 'A', 'Big O describes worst-case scenario.'),
(8, 'medium', 'What is divide and conquer?', '{"A": "Break problem into subproblems", "B": "Greedy choice", "C": "Dynamic programming", "D": "Backtracking"}', 'A', 'Solve subproblems independently and combine.'),
(8, 'medium', 'What is greedy algorithm?', '{"A": "Makes locally optimal choices", "B": "Exhaustive search", "C": "Memoization", "D": "Recursion only"}', 'A', 'Greedy aims for global optimum via local decisions.'),
(8, 'hard', 'Explain NP-completeness.', '{"A": "Problems verifiable in polynomial time but hard to solve", "B": "Solvable in polynomial time", "C": "Easy problems", "D": "Undecidable problems"}', 'A', 'NP-complete are hardest in NP class.');

-- DSA Questions
INSERT INTO questions (subject_id, difficulty, question_text, options, correct_answer, explanation) VALUES
(9, 'easy', 'What is a linked list?', '{"A": "Nodes connected by pointers", "B": "Fixed size array", "C": "Tree structure", "D": "Graph"}', 'A', 'Linked lists allow dynamic size.'),
(9, 'easy', 'What is a stack?', '{"A": "LIFO data structure", "B": "FIFO", "C": "Random access", "D": "Sorted list"}', 'A', 'Stack follows last-in-first-out.'),
(9, 'medium', 'What is binary search?', '{"A": "Search in sorted array by halving", "B": "Linear scan", "C": "Graph traversal", "D": "Hash lookup"}', 'A', 'Efficient for sorted data, O(log n).'),
(9, 'medium', 'What is a hash table?', '{"A": "Key-value storage with hashing", "B": "Sorted array", "C": "Linked list", "D": "Binary tree"}', 'A', 'Provides average O(1) access.'),
(9, 'hard', 'Explain red-black tree.', '{"A": "Self-balancing binary search tree", "B": "Unbalanced tree", "C": "Heap", "D": "Graph"}', 'A', 'Maintains balance with color properties.');

-- Computer Networks Questions
INSERT INTO questions (subject_id, difficulty, question_text, options, correct_answer, explanation) VALUES
(10, 'easy', 'What is IP address?', '{"A": "Unique identifier for devices", "B": "Protocol name", "C": "Port number", "D": "URL"}', 'A', 'IP addresses route data in networks.'),
(10, 'easy', 'What is HTTP?', '{"A": "Hypertext Transfer Protocol", "B": "File Transfer Protocol", "C": "Simple Mail Transfer Protocol", "D": "Transmission Control Protocol"}', 'A', 'HTTP is for web data transfer.'),
(10, 'medium', 'What is TCP?', '{"A": "Reliable connection-oriented protocol", "B": "Unreliable datagram", "C": "Routing protocol", "D": "Encryption protocol"}', 'A', 'TCP ensures ordered, error-checked delivery.'),
(10, 'medium', 'What is OSI model?', '{"A": "7-layer network architecture", "B": "4-layer", "C": "5-layer", "D": "3-layer"}', 'A', 'OSI defines networking functions in layers.'),
(10, 'hard', 'Explain congestion control in networks.', '{"A": "Managing traffic to prevent overload", "B": "Encrypting data", "C": "Routing packets", "D": "Addressing devices"}', 'A', 'Algorithms like TCP Tahoe/Reno handle congestion.');