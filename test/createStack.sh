#!/bin/bash

curl "http://localhost:8090/stack" \
-H "Accept:application/json" \
-H "Content-Type:application/json" \
--data @<(cat <<EOF
{
  "svn_url": "http://svn-server/app1/",
  "stack_name":"stack_test_001",
  "cluster_id":"1000",
  "images": [
    {
      "name":"sse-registry.com/app1",
      "tag":"v1.0",
      "role":"nginx" 
    }
  ]
}		
EOF
)



