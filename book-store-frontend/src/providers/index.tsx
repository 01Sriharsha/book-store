"use client";

import { Fragment, ReactNode } from "react";
import { Provider as ReduxProvider } from "react-redux";
import { Toaster } from "sonner";

import store from "@/store";
import ThemeProvider from "@/providers/theme-provider";
import AuthProvider from "@/providers/auth-provider";

export default function AppProvider({ children }: { children: ReactNode }) {
  return (
    <Fragment>
      <ThemeProvider
        attribute="class"
        defaultTheme="dark"
        forcedTheme="dark"
        enableSystem
        disableTransitionOnChange
      >
        <ReduxProvider store={store}>
          <AuthProvider>{children}</AuthProvider>
        </ReduxProvider>
      </ThemeProvider>
      <Toaster closeButton richColors position="bottom-right" />
    </Fragment>
  );
}
