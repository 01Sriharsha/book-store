"use client";

import Link from "next/link";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { RegisterSchema } from "@/lib/zod";
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

export default function RegisterForm() {
  const form = useForm<RegisterSchema>({
    resolver: zodResolver(RegisterSchema),
    defaultValues: {
      fullname: "",
      email: "",
      password: "",
      confirmPassword: "",
      city: "",
      dateOfBirth: "",
    },
  });

  const onSubmit = (values: RegisterSchema) => {
    console.log(values);
  };
  return (
    <div className="h-screen grid place-items-center">
      <Form {...form}>
        <form
          onSubmit={form.handleSubmit(onSubmit)}
          className=" bg-background border border-primary/30 p-4 rounded-xl w-full md:w-1/2"
        >
          <h1 className="text-2xl font-semibold text-primary text-center my-3">
            Register
          </h1>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <FormField
              name="fullname"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Fullname</FormLabel>
                  <FormControl>
                    <Input
                      type="text"
                      placeholder="Enter fullname"
                      {...field}
                    />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
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
              name="dateOfBirth"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Date Of Birth</FormLabel>
                  <FormControl>
                    <Input type="date" {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              name="city"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>City</FormLabel>
                  <FormControl>
                    <Input type="city" placeholder="Enter city" {...field} />
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
                      placeholder="Enter Password"
                      {...field}
                    />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              name="confirmPassword"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Confirm Password</FormLabel>
                  <FormControl>
                    <Input
                      type="text"
                      placeholder="Confirm Password"
                      {...field}
                    />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
          </div>
          <div className="mt-5 mb-3 w-full flex items-center justify-center gap-4">
            <Link href="/">
              <Button type="button" variant={"ghost"}>
                Back
              </Button>
            </Link>
            <Button type="submit">Register</Button>
          </div>
          <div className="text-center mb-2 text-sm">
            Already have an account?&nbsp;
            <Link href="/login" className="underline text-primary">
              Login
            </Link>
          </div>
        </form>
      </Form>
    </div>
  );
}
