FROM openjdk

RUN mkdir -p /app/shoppingAPI

ENV APP_NAME="rest-api-crud-shopping-fuzzy-1.0.00.jar"

COPY /target/${APP_NAME} /app/shoppingAPI

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "/app/shoppingAPI/rest-api-crud-shopping-fuzzy-1.0.00.jar" ]
