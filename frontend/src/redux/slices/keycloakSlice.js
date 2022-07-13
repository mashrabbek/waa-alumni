//import keycloak from "../../Keycloak";
import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  initialized: false,
  keycloak: null,
};

export const keycloakSlice = createSlice({
  name: "keycloak",
  initialState: initialState,
  reducers: {
    setKeycloak: (state, payload) => {
      state = { ...state, ...payload };
    },
  },
});

export default keycloakSlice.reducer;

export const { setKeycloak } = keycloakSlice.actions;
