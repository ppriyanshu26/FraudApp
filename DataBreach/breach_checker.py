"""
Core functionality for checking if an email has been involved in data breaches.
Uses the Have I Been Pwned API v3.
"""
import requests
import json
import time
from urllib.parse import quote
from colorama import Fore, Style, init

from config import HIBP_API_KEY, HIBP_API_URL, USER_AGENT, SHOW_DETAILED_BREACH_INFO

# Initialize colorama
init(autoreset=True)

class BreachChecker:
    def __init__(self):
        """Initialize the BreachChecker with API configuration."""
        if not HIBP_API_KEY:
            print(f"{Fore.RED}Error: API key not found. Please set the HIBP_API_KEY environment variable.")
            print(f"You can get an API key from: https://haveibeenpwned.com/API/Key")
            exit(1)
        
        self.headers = {
            "hibp-api-key": HIBP_API_KEY,
            "user-agent": USER_AGENT
        }
    
    def check_email(self, email):
        """
        Check if the provided email has been involved in any data breaches.
        
        Args:
            email (str): The email address to check
            
        Returns:
            dict: Information about breaches or None if no breaches found
        """
        encoded_email = quote(email)
        url = f"{HIBP_API_URL}/breachedaccount/{encoded_email}"
        
        try:
            response = requests.get(
                url, 
                headers=self.headers,
                params={"truncateResponse": not SHOW_DETAILED_BREACH_INFO}
            )
            
            if response.status_code == 200:
                return response.json()
            elif response.status_code == 404:
                return None
            elif response.status_code == 429:
                retry_after = int(response.headers.get('Retry-After', 2))
                print(f"{Fore.YELLOW}Rate limit exceeded. Retrying in {retry_after} seconds...")
                time.sleep(retry_after)
                return self.check_email(email)  # Retry after waiting
            else:
                print(f"{Fore.RED}Error: {response.status_code} - {response.text}")
                return None
                
        except requests.exceptions.RequestException as e:
            print(f"{Fore.RED}Error connecting to the API: {e}")
            return None
    
    def get_breach_details(self, breach_name):
        """
        Get detailed information about a specific breach.
        
        Args:
            breach_name (str): The name of the breach
            
        Returns:
            dict: Detailed breach information
        """
        url = f"{HIBP_API_URL}/breach/{breach_name}"
        
        try:
            response = requests.get(url, headers=self.headers)
            
            if response.status_code == 200:
                return response.json()
            else:
                print(f"{Fore.RED}Error retrieving breach details: {response.status_code}")
                return None
                
        except requests.exceptions.RequestException as e:
            print(f"{Fore.RED}Error connecting to the API: {e}")
            return None
    
    def format_breach_results(self, breaches):
        """
        Format breach results for display.
        
        Args:
            breaches (list): List of breach data
            
        Returns:
            str: Formatted breach information
        """
        if not breaches:
            return f"{Fore.GREEN}Good news! No breaches found for this email address."
        
        result = [f"{Fore.RED}Oh no! This email was found in {len(breaches)} data breach(es):"]
        
        for i, breach in enumerate(breaches, 1):
            breach_name = breach.get("Name")
            
            if SHOW_DETAILED_BREACH_INFO:
                # If we have detailed info in the response
                title = breach.get("Title", breach_name)
                breach_date = breach.get("BreachDate", "Unknown date")
                pwn_count = breach.get("PwnCount", "Unknown")
                data_classes = ", ".join(breach.get("DataClasses", ["Unknown"]))
                
                result.append(f"\n{Fore.YELLOW}Breach #{i}: {Fore.CYAN}{title}")
                result.append(f"{Fore.YELLOW}Date: {Fore.WHITE}{breach_date}")
                result.append(f"{Fore.YELLOW}Affected accounts: {Fore.WHITE}{pwn_count:,}")
                result.append(f"{Fore.YELLOW}Compromised data: {Fore.WHITE}{data_classes}")
            else:
                # If we only have the breach name
                breach_details = self.get_breach_details(breach_name)
                
                if breach_details:
                    title = breach_details.get("Title", breach_name)
                    breach_date = breach_details.get("BreachDate", "Unknown date")
                    pwn_count = breach_details.get("PwnCount", "Unknown")
                    data_classes = ", ".join(breach_details.get("DataClasses", ["Unknown"]))
                    
                    result.append(f"\n{Fore.YELLOW}Breach #{i}: {Fore.CYAN}{title}")
                    result.append(f"{Fore.YELLOW}Date: {Fore.WHITE}{breach_date}")
                    result.append(f"{Fore.YELLOW}Affected accounts: {Fore.WHITE}{pwn_count:,}")
                    result.append(f"{Fore.YELLOW}Compromised data: {Fore.WHITE}{data_classes}")
                else:
                    result.append(f"\n{Fore.YELLOW}Breach #{i}: {Fore.CYAN}{breach_name}")
        
        result.append(f"\n{Fore.YELLOW}What should you do?")
        result.append(f"{Fore.WHITE}1. Change your password for these services immediately")
        result.append(f"{Fore.WHITE}2. If you used the same password elsewhere, change those too")
        result.append(f"{Fore.WHITE}3. Consider using a password manager")
        result.append(f"{Fore.WHITE}4. Enable two-factor authentication where available")
        
        return "\n".join(result)
