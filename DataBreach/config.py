"""
Configuration settings for the password breach checker application.
"""

# API Configuration
PWNED_PASSWORDS_API_URL = "https://api.pwnedpasswords.com/range"
USER_AGENT = "PasswordBreachChecker-Python"

# Application Settings
ADD_PADDING = True  # Adds padding to API requests for enhanced privacy
