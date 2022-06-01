FROM debian:9.5-slim

ADD tag_release.sh /tag_release.sh
RUN chmod +x /tag_release.sh
RUN apt-get update && apt-get install -y curl
ENTRYPOINT ["/tag_release.sh"]
