export const environment = {
  production: true,
  apiUrl: 'http://localhost:8080/book-api/v1',
  tokenAllowedDomains: [ /localhost:8080/],
  tokenDisallowedRoutes: [/\/auth\/login/],
};
