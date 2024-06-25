"use client";

import Link from "next/link";
import { SearchIcon } from "lucide-react";
import { useAppDispatch } from "@/store";
import { toggleSearch } from "@/store/features/search-slice";
import { Button } from "@/components/ui/button";
import NavItem from "@/components/global/nav-item";

export default function Navbar() {
  const dispatch = useAppDispatch();
  return (
    <nav className="fixed inset-x-0 top-0 border-b border-b-zinc-700 z-50 bg-background">
      <div className="relative w-full py-4 px-2 flex items-center justify-between">
        <div className="flex items-center gap-6">
          <h1 className="text-xl font-semibold">Book Store</h1>
          <ul className="flex items-center gap-3">
            <NavItem label="Home" href="/" />
            <NavItem label="About" href="/about" />
            <NavItem label="Contact" href="/contact" />
          </ul>
        </div>
        <div className="flex items-center gap-3">
          <SearchIcon
            role="button"
            className=""
            onClick={() => dispatch(toggleSearch())}
          />
          <Link href="/login">
            <Button>Login</Button>
          </Link>
          <Link href="/register">
            <Button>Register</Button>
          </Link>
        </div>
      </div>
    </nav>
  );
}
