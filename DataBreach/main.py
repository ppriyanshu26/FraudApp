"""
Main entry point for the password breach checker application.
"""
import getpass
from colorama import Fore, Style, init

from password_checker import PasswordChecker

# Initialize colorama
init(autoreset=True)

def main():
    """Main function to run the password breach checker."""
    print(f"{Fore.CYAN}===================================")
    print(f"{Fore.CYAN}= Password Breach Checker =")
    print(f"{Fore.CYAN}= Using Have I Been Pwned Passwords API =")
    print(f"{Fore.CYAN}===================================\n")

    # Create password checker instance
    checker = PasswordChecker()

    # Get password from command line args or prompt
    # Note: For security, we don't accept passwords as command line arguments
    # as they would be visible in command history
    password = getpass.getpass(f"{Fore.YELLOW}Enter password to check: {Style.RESET_ALL}")

    # Validate password
    if not password:
        print(f"{Fore.RED}Error: Password cannot be empty.")
        return

    print(f"{Fore.YELLOW}Checking if password has been exposed in data breaches... Please wait.")

    # Check if password has been pwned
    is_pwned, count = checker.check_password(password)

    # Display results
    result = checker.format_password_results(is_pwned, count)
    print(f"\n{result}")

    # Clear the password from memory for security
    password = None

if __name__ == "__main__":
    main()
