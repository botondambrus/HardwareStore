import { useState } from 'react';

type Errors = {
  [key: string]: string | null;
}

function useErrors() {
  const [errors, setErrors] = useState<Errors>({});

  const setError = (field: string, error: string | null) => {
    setErrors((prev) => ({ ...prev, [field]: error }));
  };

  return { errors, setError };
}

export default useErrors;
