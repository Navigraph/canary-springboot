# Java-SpringBoot
Traditional java spring boot tech stack.
Is not using spring-data-repositories in this stack.

## Requirements

### Required
* [x] Rest API for at least one resource
  * [x] GET
  * [x] POST
  * [x] DELETE
  * [x] PUT
  * [x] Proper status codes (404, 409, 200, 201 with Location, 204)
* [x] Three tier layer architecture (or corresponding best practice for each stack).
* [x] Dependency injection (or corresponding for each test to simplify testing and configuration).
* [x] SQL database (NOSql might be fine)
* [x] Validation of dtos
* [x] Tests
  * [x] Unit tests where appropriate
  * [x] API Tests (Rest layer)
  * [x] Database integration tests (h2 in memory is fine)
* [ ] Pipeline running in Gitlab: https://docs.gitlab.com/ee/user/packages/workflows/monorepo.html
  * [ ] Build
  * [ ] Test with reports
  * [ ] Sonar analysis, with code coverage 
* [ ] Containerization
  * [ ] Build container
  * [ ] Push to container registry

### Optional
* [ ] Swagger
* [ ] GraphQL API
* [ ] Metrics
* [ ] Securing the API
* [ ] HATEOAS
* [ ] Profile support for different dev properties (h2 database, mocking of data...)
* [ ] External integration (http, messaging)
* [ ] Database migrations
