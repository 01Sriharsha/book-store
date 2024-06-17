export type ApiResponse<T> = {
  data: T;
  message: string;
  properties: Record<string, object>;
};

export type User = {
  id: number;
  fullname: string;
  email: string;
  city: string;
  dateOfBirth: Date;
  enabled: boolean;
  roles: { id: number; name: string }[];
  createdAt: Date;
  updatedAt: Date;
};
