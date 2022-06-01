#!/bin/sh -l

sh -c "
TARGET_URL=\"$INPUT_GITHUB_API_URL/repos/$INPUT_GITHUB_REPOSITORY/releases?access_token=$INPUT_GITHUB_TOKEN\";

curl -k -X POST -H \"Content-Type: application/json\" \$TARGET_URL -d @- << EOF
{
\"tag_name\": \"$INPUT_BUILD_NAME\",
\"target_commitish\": \"$INPUT_GITHUB_TARGET_BRANCH\",
\"name\": \"$INPUT_BUILD_NAME\",
\"body\": \"Release of $INPUT_BUILD_NAME\",
\"draft\": true,
\"prerelease\": true
}
EOF
"
