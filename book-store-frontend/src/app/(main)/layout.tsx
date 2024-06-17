import { Fragment, ReactNode } from "react";
import Navbar from "@/components/global/navbar";

export default function MainLayout({
  children,
}: Readonly<{
  children: ReactNode;
}>) {
  return (
    <Fragment>
      <header>
        <Navbar />
      </header>
      <main>{children}</main>
    </Fragment>
  );
}
