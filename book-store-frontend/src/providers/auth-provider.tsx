"use client";

import { Fragment, ReactNode, useEffect } from "react";
import { useRouter } from "next/navigation";
import { useAppSelector } from "@/store";

export default function AuthProvider({ children }: { children: ReactNode }) {
  const router = useRouter();
  const { isAuthenticated } = useAppSelector((state) => state.auth);

  useEffect(() => {
    if (!isAuthenticated) {
      router.replace("/login");
    } else {
      router.replace("/");
    }
  }, [router, isAuthenticated]);

  return <Fragment>{children}</Fragment>;
}
