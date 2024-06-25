"use client";

import { FormEvent, useEffect, useRef, useState } from "react";
import { cn } from "@/lib/utils";
import { useAppDispatch, useAppSelector } from "@/store";
import { updateSearchTerm } from "@/store/features/search-slice";
import { Input } from "@/components/ui/input";

export default function Search() {
  const searchRef = useRef<HTMLInputElement>(null);
  const dispatch = useAppDispatch();

  const { open, searchTerm } = useAppSelector((state) => state.search);
  const [keyword, setKeyword] = useState("");

  useEffect(() => {
    if (open && searchRef.current) {
      searchRef.current.focus();
    }
  }, [open]);

  const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    dispatch(updateSearchTerm(keyword));
  };

  return (
    <form
      onSubmit={handleSubmit}
      className={cn(
        "transition-transform duration-300 ease-linear",
        open ? "translate-y-5" : "opacity-20 translate-y-[-400px]",
        "z-[999] w-full flex justify-center px-1"
      )}
    >
      <Input
        ref={searchRef}
        type="search"
        placeholder="Search Here..."
        value={keyword}
        onChange={(e) => setKeyword(e.target.value)}
        className="w-full md:w-96"
      />
    </form>
  );
}
