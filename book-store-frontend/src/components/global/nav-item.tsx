"use client";

import Link from "next/link";
import { usePathname } from "next/navigation";
import { cn } from "@/lib/utils";

type NavItemProps = {
  label: string;
  href: string;
  className?: string;
};

export default function NavItem({ href, label, className }: NavItemProps) {
  const pathname = usePathname();
  const isActive = pathname === href;
  return (
    <Link href={href}>
      <li
        className={cn(
          "hover:bg-primary hover:text-background transition-all duration-300 ease-in-out p-1 rounded-md",
          isActive &&
            "border-b-2 border-b-gray-600 rounded-none hover:bg-transparent hover:text-foreground",
          className
        )}
      >
        {label}
      </li>
    </Link>
  );
}
