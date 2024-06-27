"use client";

import { FormEvent, useEffect, useMemo, useRef, useState } from "react";
import { SearchIcon } from "lucide-react";
import { cn } from "@/lib/utils";
import { useAppDispatch, useAppSelector } from "@/store";
import { toggleSearch, updateSearchTerm } from "@/store/features/search-slice";
import { books } from "@/util/books";
import SearchCard from "./search-card";

export default function Search() {
  const searchRef = useRef<HTMLInputElement>(null);
  const dispatch = useAppDispatch();

  const { open, searchTerm } = useAppSelector((state) => state.search);
  const [keyword, setKeyword] = useState("");

  const filteredBooks = useMemo(() => {
    const key = keyword.toLowerCase();
    return books.filter(
      (book) =>
        book.title.toLowerCase().includes(key) ||
        book.summary.toLowerCase().includes(key)
    );
  }, [keyword]);

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
      onBlur={() => dispatch(toggleSearch())}
      className={cn(
        "transition-transform duration-300 ease-linear w-full md:w-96 mx-auto flex flex-col gap-2",
        open ? "translate-y-5" : "opacity-20 translate-y-[-400px]",
        "z-[999] w-full flex justify-center px-1"
      )}
    >
      <div
        className={cn(
          "relative flex items-center gap-2 rounded-lg bg-background px-4",
          open && "ring-2 ring-primary/30"
        )}
      >
        <input
          ref={searchRef}
          type="search"
          placeholder="Search Here..."
          value={keyword}
          onChange={(e) => setKeyword(e.target.value)}
          className="w-full py-4 px-2 focus:outline-none rounded-lg bg-background"
        />
        <button type="submit">
          <SearchIcon className="hover:text-primary" />
        </button>
      </div>
      {open && keyword && (
        <div className="space-y-2 rounded-xl h-96 overflow-y-scroll">
          {filteredBooks.map((book) => (
            <SearchCard
              key={book.id}
              title={book.title}
              description={book.summary}
            />
          ))}
        </div>
      )}
    </form>
  );
}
