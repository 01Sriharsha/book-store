import { Fragment, ReactNode } from "react";
import Navbar from "@/components/global/navbar";
import Search from "@/components/global/search";

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
      <main className="pt-20">
        <Search />
        {children}
      </main>
    </Fragment>
  );
}
