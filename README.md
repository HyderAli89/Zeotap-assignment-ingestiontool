# Bidirectional ClickHouse & Flat File Data Ingestion Tool

## Objective
This web-based application facilitates bidirectional data ingestion between ClickHouse and Flat Files (CSV). The tool supports JWT token-based authentication for ClickHouse as a source, allows users to select specific columns for ingestion, and reports the total number of records processed upon completion.

## Core Features
- **Bidirectional Ingestion**: 
  - ClickHouse -> Flat File (CSV).
  - Flat File -> ClickHouse.
- **User Interface**:
  - Allows selection of data source (ClickHouse or Flat File).
  - Configurable connection for ClickHouse (Host, Port, Database, User, JWT Token).
  - Flat File configuration (File name, Delimiters).
  - Schema discovery and column selection.
- **Completion Reporting**: Displays the total number of records processed.
- **Error Handling**: Basic error handling for connection, authentication, query, and ingestion.

## Features Overview
1. **ClickHouse to Flat File**: Users can select tables and columns from ClickHouse and export them to a CSV file.
2. **Flat File to ClickHouse**: Users can upload CSV files and import data into a specified ClickHouse table.
3. **JWT Token Authentication**: Secure authentication using JWT tokens for ClickHouse connection.
4. **Multi-Table Joins** (Bonus): Users can select multiple tables from ClickHouse and define JOIN conditions for ingestion.
5. **Optional Enhancements**:
   - **Progress Bar**: Visual indicator of ingestion progress.
   - **Data Preview**: Preview the first 100 records before ingestion.

## Setup & Installation
### Prerequisites
- Docker (for running ClickHouse locally)
- Node.js (for frontend)
- Java (for backend, if using a Java implementation)
- JWT token for ClickHouse authentication

### Backend Setup
1. Clone the repository:
   ```bash
   git clone https://github.com/your-repo/bidirectional-data-ingestion
   cd bidirectional-data-ingestion
