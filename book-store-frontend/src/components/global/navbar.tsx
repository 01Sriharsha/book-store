import { Button } from "@/components/ui/button";
import NavItem from "@/components/global/nav-item";

export default function Navbar() {
  return (
    <nav className="fixed inset-x-0 top-0 border border-gray-700 w-full py-4 px-2 flex items-center justify-between z-50">
      <div className="flex items-center gap-6">
        <h1 className="text-xl font-semibold">Book Store</h1>
        <ul className="flex items-center gap-3">
          <NavItem label="Home" href="/" />
          <NavItem label="About" href="/about" />
          <NavItem label="Contact" href="/contact" />
        </ul>
      </div>
      <ul className="flex items-center gap-3">
        <Button asChild>
          <NavItem label="Login" href="/login" />
        </Button>
        <Button asChild>
          <NavItem label="Register" href="/register" />
        </Button>
      </ul>
    </nav>
  );
}
