"use client";

export const getlocalstorage = <T>(key: string): T | null => {
  if (key && typeof window !== "undefined") {
    const data = localStorage.getItem(key);
    if (data) {
      return JSON.parse(data) as T;
    }
  }
  return null;
};
