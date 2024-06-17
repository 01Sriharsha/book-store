"use client";

import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import { User } from "@/types";
import { getlocalstorage } from "@/util/localstorage";

const STORAGE_KEY = "user";

type AuthState = {
  isAuthenticated: boolean;
  data: User | null;
};

const initialState: AuthState = {
  isAuthenticated: !!getlocalstorage<boolean>(STORAGE_KEY),
  data: getlocalstorage(STORAGE_KEY),
};

const authSlice = createSlice({
  name: "auth",
  initialState,
  reducers: {
    authenticate: (state, action: PayloadAction<User>) => {
      state.isAuthenticated = true;
      state.data = action.payload;
      localStorage.setItem(STORAGE_KEY, JSON.stringify(state.data));
    },
    logout: (state) => {
      state.isAuthenticated = false;
      state.data = null;
      localStorage.removeItem(STORAGE_KEY);
    },
  },
});

export const { authenticate, logout } = authSlice.actions;

const authReducer = authSlice.reducer;

export default authReducer;
