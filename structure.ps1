function Create-ProjectStructure {
    param(
        [string]$ProjectRoot = "frontend"
    )
    
    # Base directories
    $directories = @(
        # Root
        $ProjectRoot,
        
        # Public
        "$ProjectRoot\public",
        "$ProjectRoot\public\assets",
        "$ProjectRoot\public\assets\images", 
        "$ProjectRoot\public\assets\icons",
        "$ProjectRoot\public\assets\fonts",
        
        # Source
        "$ProjectRoot\src",
        
        # App
        "$ProjectRoot\src\app",
        "$ProjectRoot\src\app\store",
        "$ProjectRoot\src\app\router", 
        "$ProjectRoot\src\app\providers",
        
        # Features
        "$ProjectRoot\src\features",
        "$ProjectRoot\src\features\auth",
        "$ProjectRoot\src\features\auth\components",
        "$ProjectRoot\src\features\auth\components\LoginForm",
        "$ProjectRoot\src\features\auth\components\RegisterForm",
        "$ProjectRoot\src\features\auth\components\OTPVerification",
        "$ProjectRoot\src\features\auth\pages",
        "$ProjectRoot\src\features\auth\pages\Login",
        "$ProjectRoot\src\features\auth\pages\Register", 
        "$ProjectRoot\src\features\auth\pages\ForgotPassword",
        "$ProjectRoot\src\features\auth\hooks",
        "$ProjectRoot\src\features\auth\services",
        "$ProjectRoot\src\features\auth\utils",
        
        "$ProjectRoot\src\features\user",
        "$ProjectRoot\src\features\user\components",
        "$ProjectRoot\src\features\user\components\UserProfile",
        "$ProjectRoot\src\features\user\components\EditProfile",
        "$ProjectRoot\src\features\user\components\ProgressTracker",
        "$ProjectRoot\src\features\user\pages",
        "$ProjectRoot\src\features\user\pages\Profile",
        "$ProjectRoot\src\features\user\pages\Settings",
        "$ProjectRoot\src\features\user\pages\Dashboard",
        "$ProjectRoot\src\features\user\hooks",
        "$ProjectRoot\src\features\user\services",
        
        "$ProjectRoot\src\features\interviews",
        "$ProjectRoot\src\features\interviews\components",
        "$ProjectRoot\src\features\interviews\components\InterviewRoom",
        "$ProjectRoot\src\features\interviews\components\VideoRecorder",
        "$ProjectRoot\src\features\interviews\components\QuestionBank", 
        "$ProjectRoot\src\features\interviews\components\FeedbackPanel",
        "$ProjectRoot\src\features\interviews\components\Timer",
        "$ProjectRoot\src\features\interviews\pages",
        "$ProjectRoot\src\features\interviews\pages\InterviewLobby",
        "$ProjectRoot\src\features\interviews\pages\LiveInterview",
        "$ProjectRoot\src\features\interviews\pages\InterviewHistory",
        "$ProjectRoot\src\features\interviews\pages\Results",
        "$ProjectRoot\src\features\interviews\hooks",
        "$ProjectRoot\src\features\interviews\services",
        
        "$ProjectRoot\src\features\mock-tests",
        "$ProjectRoot\src\features\mock-tests\components",
        "$ProjectRoot\src\features\mock-tests\components\TestSelector",
        "$ProjectRoot\src\features\mock-tests\components\TestSession",
        "$ProjectRoot\src\features\mock-tests\components\CodingEditor",
        "$ProjectRoot\src\features\mock-tests\components\Whiteboard",
        "$ProjectRoot\src\features\mock-tests\components\ResultsAnalyzer",
        "$ProjectRoot\src\features\mock-tests\pages",
        "$ProjectRoot\src\features\mock-tests\pages\TestLibrary",
        "$ProjectRoot\src\features\mock-tests\pages\TestSession", 
        "$ProjectRoot\src\features\mock-tests\pages\TestResults",
        "$ProjectRoot\src\features\mock-tests\pages\PerformanceAnalytics",
        "$ProjectRoot\src\features\mock-tests\hooks",
        "$ProjectRoot\src\features\mock-tests\services",
        
        "$ProjectRoot\src\features\banter-area",
        "$ProjectRoot\src\features\banter-area\components",
        "$ProjectRoot\src\features\banter-area\components\ChatRoom",
        "$ProjectRoot\src\features\banter-area\components\MessageBoard",
        "$ProjectRoot\src\features\banter-area\components\PollCreator",
        "$ProjectRoot\src\features\banter-area\components\MemeGenerator",
        "$ProjectRoot\src\features\banter-area\components\IceBreakers",
        "$ProjectRoot\src\features\banter-area\pages",
        "$ProjectRoot\src\features\banter-area\pages\CommunityChat",
        "$ProjectRoot\src\features\banter-area\pages\DiscussionForums",
        "$ProjectRoot\src\features\banter-area\pages\FunZone",
        "$ProjectRoot\src\features\banter-area\pages\Events",
        "$ProjectRoot\src\features\banter-area\hooks",
        "$ProjectRoot\src\features\banter-area\services",
        
        "$ProjectRoot\src\features\skill-builders",
        "$ProjectRoot\src\features\skill-builders\components",
        "$ProjectRoot\src\features\skill-builders\components\CodingChallenges",
        "$ProjectRoot\src\features\skill-builders\components\SystemDesignBoard",
        "$ProjectRoot\src\features\skill-builders\components\BehavioralQuestions",
        "$ProjectRoot\src\features\skill-builders\components\ResumeBuilder",
        "$ProjectRoot\src\features\skill-builders\components\PortfolioShowcase",
        "$ProjectRoot\src\features\skill-builders\pages",
        "$ProjectRoot\src\features\skill-builders\pages\CodingPractice",
        "$ProjectRoot\src\features\skill-builders\pages\SystemDesign",
        "$ProjectRoot\src\features\skill-builders\pages\BehavioralPrep",
        "$ProjectRoot\src\features\skill-builders\pages\ResumeLab",
        "$ProjectRoot\src\features\skill-builders\pages\PortfolioWorkshop",
        "$ProjectRoot\src\features\skill-builders\hooks",
        "$ProjectRoot\src\features\skill-builders\services",
        
        "$ProjectRoot\src\features\gamification",
        "$ProjectRoot\src\features\gamification\components",
        "$ProjectRoot\src\features\gamification\components\Leaderboard",
        "$ProjectRoot\src\features\gamification\components\Achievements",
        "$ProjectRoot\src\features\gamification\components\ProgressBars",
        "$ProjectRoot\src\features\gamification\components\Badges",
        "$ProjectRoot\src\features\gamification\components\RewardsShop",
        "$ProjectRoot\src\features\gamification\pages",
        "$ProjectRoot\src\features\gamification\pages\ProfileStats",
        "$ProjectRoot\src\features\gamification\pages\AchievementHall",
        "$ProjectRoot\src\features\gamification\pages\RewardsCenter",
        "$ProjectRoot\src\features\gamification\hooks",
        "$ProjectRoot\src\features\gamification\services",
        
        "$ProjectRoot\src\features\ai-coach",
        "$ProjectRoot\src\features\ai-coach\components",
        "$ProjectRoot\src\features\ai-coach\components\AIChatAssistant",
        "$ProjectRoot\src\features\ai-coach\components\FeedbackGenerator",
        "$ProjectRoot\src\features\ai-coach\components\PerformancePredictor",
        "$ProjectRoot\src\features\ai-coach\components\InterviewSimulator",
        "$ProjectRoot\src\features\ai-coach\components\ResumeAnalyzer",
        "$ProjectRoot\src\features\ai-coach\pages",
        "$ProjectRoot\src\features\ai-coach\pages\AICoachDashboard",
        "$ProjectRoot\src\features\ai-coach\pages\PersonalizedFeedback",
        "$ProjectRoot\src\features\ai-coach\pages\AIInterviewPrep",
        "$ProjectRoot\src\features\ai-coach\hooks",
        "$ProjectRoot\src\features\ai-coach\services",
        
        # Shared
        "$ProjectRoot\src\shared",
        "$ProjectRoot\src\shared\components",
        "$ProjectRoot\src\shared\components\ui",
        "$ProjectRoot\src\shared\components\ui\Button",
        "$ProjectRoot\src\shared\components\ui\Input",
        "$ProjectRoot\src\shared\components\ui\Modal",
        "$ProjectRoot\src\shared\components\ui\Loader",
        "$ProjectRoot\src\shared\components\ui\VideoPlayer",
        "$ProjectRoot\src\shared\components\ui\VoiceRecorder",
        "$ProjectRoot\src\shared\components\layout",
        "$ProjectRoot\src\shared\components\layout\Header",
        "$ProjectRoot\src\shared\components\layout\Footer",
        "$ProjectRoot\src\shared\components\layout\Sidebar",
        "$ProjectRoot\src\shared\components\layout\Navigation",
        "$ProjectRoot\src\shared\components\layout\LayoutGrid",
        "$ProjectRoot\src\shared\components\forms",
        "$ProjectRoot\src\shared\components\forms\FormField",
        "$ProjectRoot\src\shared\components\forms\FormValidator",
        "$ProjectRoot\src\shared\components\forms\MultiStepForm",
        "$ProjectRoot\src\shared\hooks",
        "$ProjectRoot\src\shared\services",
        "$ProjectRoot\src\shared\utils",
        "$ProjectRoot\src\shared\styles",
        "$ProjectRoot\src\shared\styles\themes"
    )
    
    # Create directories
    Write-Host "Creating project structure..." -ForegroundColor Green
    Write-Host "Project Root: $ProjectRoot" -ForegroundColor Yellow
    
    $createdCount = 0
    foreach ($dir in $directories) {
        if (!(Test-Path $dir)) {
            New-Item -ItemType Directory -Path $dir -Force | Out-Null
            Write-Host "Created: $dir" -ForegroundColor Cyan
            $createdCount++
        }
    }
    
    # Create empty index files for features
    $indexFiles = @(
        "$ProjectRoot\src\features\auth\index.js",
        "$ProjectRoot\src\features\user\index.js", 
        "$ProjectRoot\src\features\interviews\index.js",
        "$ProjectRoot\src\features\mock-tests\index.js",
        "$ProjectRoot\src\features\banter-area\index.js",
        "$ProjectRoot\src\features\skill-builders\index.js",
        "$ProjectRoot\src\features\gamification\index.js",
        "$ProjectRoot\src\features\ai-coach\index.js"
    )
    
    foreach ($file in $indexFiles) {
        if (!(Test-Path $file)) {
            New-Item -ItemType File -Path $file -Force | Out-Null
            Write-Host "Created: $file" -ForegroundColor Cyan
            $createdCount++
        }
    }
    
    # Create main application files
    $mainFiles = @(
        "$ProjectRoot\src\main.jsx",
        "$ProjectRoot\src\App.jsx",
        "$ProjectRoot\src\App.css",
        "$ProjectRoot\package.json",
        "$ProjectRoot\vite.config.js",
        "$ProjectRoot\index.html"
    )
    
    foreach ($file in $mainFiles) {
        if (!(Test-Path $file)) {
            New-Item -ItemType File -Path $file -Force | Out-Null
            Write-Host "Created: $file" -ForegroundColor Cyan
            $createdCount++
        }
    }
    
    # Create shared style files
    $styleFiles = @(
        "$ProjectRoot\src\shared\styles\globals.css",
        "$ProjectRoot\src\shared\styles\variables.css",
        "$ProjectRoot\src\shared\styles\mixins.css",
        "$ProjectRoot\src\shared\styles\animations.css",
        "$ProjectRoot\src\shared\styles\themes\light.js",
        "$ProjectRoot\src\shared\styles\themes\dark.js"
    )
    
    foreach ($file in $styleFiles) {
        if (!(Test-Path $file)) {
            New-Item -ItemType File -Path $file -Force | Out-Null
            Write-Host "Created: $file" -ForegroundColor Cyan
            $createdCount++
        }
    }
    
}
Create-ProjectStructure -ProjectRoot "frontend"