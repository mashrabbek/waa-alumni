import Keycloak from "keycloak-js";

const keycloak = new Keycloak({
  url: "http://localhost:8081/",
  realm: "waa",
  clientId: "reactu",
  "ssl-required": "external",
  "public-client": true,
  "confidential-port": 0,
});

// keycloak
//   .init({
//     onLoad: "login-required",
//     promiseType: "native",
//   })
//   .then((auth) => {
//     if (auth) {
//       console.log("token", keycloak.token);
//     }
//   })
//   .catch((err) => {
//     console.error("Log:"+err);
//   });
/*
{
  "realm": "waa",
  "auth-server-url": "http://localhost:8081/auth/",
  "ssl-required": "external",
  "resource": "react-auth",
  "public-client": true,
  "confidential-port": 0
}
  */

export default keycloak;
