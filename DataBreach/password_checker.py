"""
Core functionality for checking if a password has been exposed in data breaches.
Uses the Have I Been Pwned Pwned Passwords API (which is free and doesn't require an API key).
"""
import hashlib
import requests
from colorama import Fore, Style, init

from config import PWNED_PASSWORDS_API_URL, USER_AGENT, ADD_PADDING

# Initialize colorama
init(autoreset=True)

class PasswordChecker:
    def __init__(self):
        """Initialize the PasswordChecker with API configuration."""
        self.headers = {
            "user-agent": USER_AGENT
        }
        
        # Add padding header if enabled
        if ADD_PADDING:
            self.headers["Add-Padding"] = "true"
    
    def check_password(self, password):
        """
        Check if the provided password has been exposed in data breaches.
        Uses k-anonymity model to protect the password.
        
        Args:
            password (str): The password to check
            
        Returns:
            tuple: (is_pwned, count) where:
                - is_pwned (bool): True if password was found in breaches
                - count (int): Number of times the password appeared in breaches
        """
        # Convert the password to a SHA-1 hash
        password_hash = hashlib.sha1(password.encode('utf-8')).hexdigest().upper()
        
        # Split the hash into prefix and suffix for k-anonymity
        prefix, suffix = password_hash[:5], password_hash[5:]
        
        try:
            # Query the API with just the prefix
            response = requests.get(
                f"{PWNED_PASSWORDS_API_URL}/{prefix}",
                headers=self.headers
            )
            
            if response.status_code == 200:
                # Check if our suffix is in the response
                hash_count = self._get_hash_count(response.text, suffix)
                return (hash_count > 0, hash_count)
            else:
                print(f"{Fore.RED}Error: {response.status_code} - {response.text}")
                return (False, 0)
                
        except requests.exceptions.RequestException as e:
            print(f"{Fore.RED}Error connecting to the API: {e}")
            return (False, 0)
    
    def _get_hash_count(self, response_text, hash_suffix):
        """
        Parse the API response to find if our hash suffix exists and how many times.
        
        Args:
            response_text (str): The text response from the API
            hash_suffix (str): The suffix of our password hash to look for
            
        Returns:
            int: The count of how many times the password appears in breaches, or 0 if not found
        """
        for line in response_text.splitlines():
            # Each line is in format: SUFFIX:COUNT
            parts = line.split(':')
            if len(parts) == 2:
                if parts[0] == hash_suffix:
                    return int(parts[1])
        return 0
    
    def format_password_results(self, is_pwned, count):
        """
        Format password check results for display.
        
        Args:
            is_pwned (bool): Whether the password was found in breaches
            count (int): Number of times the password appeared in breaches
            
        Returns:
            str: Formatted result information
        """
        if not is_pwned:
            return f"{Fore.GREEN}Good news! This password hasn't been found in any known data breaches."
        
        result = [f"{Fore.RED}Oh no! This password has been found in data breaches {count:,} times."]
        
        result.append(f"\n{Fore.YELLOW}What does this mean?")
        result.append(f"{Fore.WHITE}This password has been exposed in data breaches and is unsafe to use.")
        
        result.append(f"\n{Fore.YELLOW}What should you do?")
        result.append(f"{Fore.WHITE}1. If you're using this password anywhere, change it immediately")
        result.append(f"{Fore.WHITE}2. Never reuse passwords across different websites or services")
        result.append(f"{Fore.WHITE}3. Consider using a password manager to generate and store strong, unique passwords")
        result.append(f"{Fore.WHITE}4. Enable two-factor authentication where available")
        
        result.append(f"\n{Fore.YELLOW}Password security tips:")
        result.append(f"{Fore.WHITE}• Use long passwords (at least 12 characters)")
        result.append(f"{Fore.WHITE}• Include a mix of uppercase, lowercase, numbers, and special characters")
        result.append(f"{Fore.WHITE}• Avoid common words, phrases, or personal information")
        result.append(f"{Fore.WHITE}• Use a different password for each account")
        
        return "\n".join(result)
