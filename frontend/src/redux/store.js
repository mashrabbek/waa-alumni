import { configureStore } from "@reduxjs/toolkit";
import keycloakSlice from "./slices/keycloakSlice";

export const store = configureStore({
  reducer: {
    keycloak: keycloakSlice,
  },
});
