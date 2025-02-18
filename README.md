# Secure Token Service
Exodus STS is an open-source Secure Token Service (STS) solution designed to  issue, store, verify and revoke tokens. It is based on OIDC, but with limited functionality.

## Key features
- Issuing bearer tokens
- Validating tokens
- Revoking tokens

## Installation
### Docker
The project includes a `Dockerfile` that allows you to containerize the Exodus IAM Service for deployment in Docker environments.
The project currently depends on the [JSend library](https://github.com/Maros1077/exodus-jsend-network), that must be included within the source files.
### Database
All the data are stored using in a database using Hibernate.
TODO
