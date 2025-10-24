-- Insert test user (password is "password123" encrypted)
INSERT INTO users (username, email, password, full_name, enabled, created_at, updated_at) 
VALUES (
    'testuser', 
    'test@example.com', 
    '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a', 
    'Test User', 
    true, 
    NOW(), 
    NOW()
)
ON CONFLICT (username) DO NOTHING;