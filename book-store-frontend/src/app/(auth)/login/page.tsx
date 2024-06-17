"use client";

import { useCallback } from "react";
import { useRouter } from "next/navigation";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { toast } from "sonner";
import { Loader } from "lucide-react";

import { useAppDispatch } from "@/store";
import { authenticate } from "@/store/features/auth-slice";
import { LoginSchema } from "@/lib/zod";
import { useAxios } from "@/hooks/use-axios";
import { ApiResponse, User } from "@/types";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";

export default function LoginPage() {
  const form = useForm<LoginSchema>({
    resolver: zodResolver(LoginSchema),
    defaultValues: {
      email: "",
      password: "",
    },
  });

  const router = useRouter();
  const dispatch = useAppDispatch();
  const { loading, mutate } = useAxios();

  const onSubmit = useCallback(
    async (values: LoginSchema) => {
      console.log(values);
      const { data, error } = await mutate<ApiResponse<User>>(
        "post",
        "/auth/login",
        values
      );
      if (error) {
        toast.error(error);
      } else if (data) {
        dispatch(authenticate(data.data));
        toast.success(data.message);
        router.replace("/");
      }
    },
    [mutate, router, dispatch]
  );
  return (
    <div className="h-screen grid place-items-center">
      <Form {...form}>
        <form
          onSubmit={form.handleSubmit(onSubmit)}
          className="w-[400px] p-4 rounded-xl border border-gray-700 shadow-sm"
        >
          <h1 className="text-2xl text-center font-semibold my-3">Login</h1>
          <div className="space-y-4">
            <FormField
              name="email"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Email</FormLabel>
                  <FormControl>
                    <Input type="email" placeholder="Enter email" {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              name="password"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Password</FormLabel>
                  <FormControl>
                    <Input
                      type="password"
                      placeholder="Enter password"
                      {...field}
                    />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
          </div>
          <Button disabled={loading} type="submit" className="w-full my-4">
            {loading ? <Loader className="animate-spin" /> : "Submit"}
          </Button>
        </form>
      </Form>
    </div>
  );
}
