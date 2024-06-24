"use client";

import { useState } from "react";
import { Input } from "../ui/input";
import { SearchIcon } from "lucide-react";

export default function Search() {
  const [keyword, setKeyword] = useState("");
  const [show, setShow] = useState(false);
  const toggleIcon = () => setShow(!show);
  return (
    <div className="relative">
      <SearchIcon role="button" className="" onClick={toggleIcon} />
      {show && (
        <div className="absolute top-16 right-1 w-[400px]">
          <Input
            type="search"
            placeholder="Search Here..."
            value={keyword}
            onChange={(e) => setKeyword(e.target.value)}
            autoFocus
          />
        </div>
      )}
    </div>
  );
}
