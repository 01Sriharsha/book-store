import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { cn } from "@/lib/utils";

type SearchCardProps = {
  title?: string;
  description?: string;
  className?: string;
};

const SearchCard = ({ title, description, className }: SearchCardProps) => {
  return (
    <Card
      className={cn("flex flex-col gap-2 w-full rounded-xl p-3", className)}
    >
      <CardContent className="p-0 space-y-1">
        <CardTitle className="text-lg">{title}</CardTitle>
        <CardDescription className="text-sm">{description}</CardDescription>
      </CardContent>
    </Card>
  );
};

export default SearchCard;
