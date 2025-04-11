# Password Breach Checker

A Python application that checks if a password has been exposed in data breaches using the free Have I Been Pwned Pwned Passwords API.

## Features

- Check if a password has been exposed in data breaches
- Uses k-anonymity to protect your password during checking
- Provide recommendations on password security
- Completely free - no API key required

## How It Works

This application uses the k-anonymity model to securely check if a password has been exposed in data breaches:

1. Your password is converted to a SHA-1 hash locally on your machine
2. Only the first 5 characters of the hash are sent to the API
3. The API returns a list of hash suffixes (remaining characters) that match the prefix
4. Your application checks locally if your full hash is in the returned list
5. Your complete password or full hash is never sent over the network

## Prerequisites

- Python 3.6 or higher

## Installation

1. Clone this repository or download the files
2. Install the required packages:

```bash
pip install -r requirements.txt
```

## Usage

Run the application from the command line:

```bash
python main.py
```

The application will prompt you to enter a password securely (the input will be hidden).

## Configuration

You can modify the `config.py` file to change application settings:

- `ADD_PADDING`: Set to `True` to add padding to API requests for enhanced privacy
- `USER_AGENT`: The user agent string sent with API requests

## Security Notes

- Your password is never sent over the network
- Only a partial hash (first 5 characters) is transmitted
- The application uses the `getpass` module to securely input passwords
- The password is cleared from memory after checking

## License

This project is open source and available under the MIT License.

## Acknowledgements

- This application uses the [Have I Been Pwned Pwned Passwords API](https://haveibeenpwned.com/API/v3#PwnedPasswords) by Troy Hunt
- The k-anonymity implementation is based on [Troy Hunt's blog post](https://www.troyhunt.com/ive-just-launched-pwned-passwords-version-2)
