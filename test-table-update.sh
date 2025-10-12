#!/bin/bash

# Test script for table status update
# Make sure to replace the JWT token with a valid one from your login

BASE_URL="http://localhost:8080"
JWT_TOKEN="your_jwt_token_here"
TABLE_ID="1"

echo "Testing table status update..."

# First, you need to get a JWT token by logging in
echo "1. First, login to get JWT token:"
echo "curl -X POST $BASE_URL/api/auth/v1/login \\"
echo "  -H \"Content-Type: application/json\" \\"
echo "  -d '{\"username\":\"admin\",\"password\":\"Passw0rd\"}'"
echo ""

echo "2. Then update table status:"
echo "curl -X PUT $BASE_URL/api/tables/v1/$TABLE_ID \\"
echo "  -H \"Content-Type: application/json\" \\"
echo "  -H \"Authorization: Bearer \$JWT_TOKEN\" \\"
echo "  -d '{\"status\":\"OCCUPIED\",\"paymentStatus\":\"PENDING\"}'"
echo ""

# Example with actual values (uncomment and replace JWT_TOKEN)
# curl -X PUT $BASE_URL/api/tables/v1/$TABLE_ID \
#   -H "Content-Type: application/json" \
#   -H "Authorization: Bearer $JWT_TOKEN" \
#   -d '{"status":"OCCUPIED","paymentStatus":"PENDING"}'

echo "Expected flow:"
echo "1. Update table via REST API"
echo "2. Kafka producer sends message to 'table-status' topic"
echo "3. Kafka consumer receives message and broadcasts via Socket.IO"
echo "4. Socket.IO clients receive 'tableStatusUpdate' event"
