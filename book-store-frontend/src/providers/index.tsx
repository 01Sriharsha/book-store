"use client";

import { Fragment, ReactNode } from "react";
import { ThemeProvider } from "@/providers/theme-provider";
import { Provider as ReduxProvider } from "react-redux";
import store from "@/store";

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
        <ReduxProvider store={store}>{children}</ReduxProvider>
      </ThemeProvider>
    </Fragment>
  );
}
