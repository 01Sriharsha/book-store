import { z } from "zod";

export const LoginSchema = z.object({
  email: z.string().email("Invalid email"),
  password: z.string().min(5, "Password must be at least 5 characters"),
});

export const RegisterSchema = z
  .object({
    fullname: z.string().min(1, { message: "Fullname is required" }),
    email: z.string().email("Invalid email"),
    city: z.string().min(1, { message: "City is required" }),
    dateOfBirth: z.string().min(1, { message: "Date of birth is required" }),
    password: z.string().min(5, "Password must be at least 5 characters"),
    confirmPassword: z.string(),
  })
  .refine((data) => data.password === data.confirmPassword, {
    message: "Passwords don't match",
    path: ["confirmPassword"], // specify the path to show the error
  });

export type LoginSchema = z.infer<typeof LoginSchema>;
export type RegisterSchema = z.infer<typeof RegisterSchema>;
