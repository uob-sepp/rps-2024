FROM gradle:8.10.2
WORKDIR /usr/src/rps
COPY . .
RUN gradle installDist

FROM gradle:jdk21
WORKDIR /root/
COPY --from=0 /usr/src/rps/app/build/install/app .
CMD ["./bin/app"]
