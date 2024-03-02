import { useState, useEffect } from 'react';
import { SelectChangeEvent, Theme, createTheme } from '@mui/material';
import { orange } from '@mui/material/colors';

const themes = {
  light: createTheme({
    palette: {
      mode: 'light',
    },
  }),
  dark: createTheme({
    palette: {
      mode: 'dark',
    },
  }),
  custom: createTheme({
    palette: {
      mode: 'light',
      primary: orange,
      secondary: {
        main: '#ffffff',
      },
    },
  }),
} as const;

export type ThemeName = keyof typeof themes;

export const useThemeSwitcher = () => {
  const storedTheme = localStorage.getItem('theme') as ThemeName || 'light';

  const [theme, setTheme] = useState<Theme>(themes[storedTheme]);
  const [themeName, setThemeName] = useState<ThemeName>(storedTheme);

  useEffect(() => {
    localStorage.setItem('theme', themeName);
  }, [themeName]);

  const changeTheme = (event: SelectChangeEvent<string>) => {
    const selectedTheme = event.target.value as ThemeName;
  
    setTheme(themes[selectedTheme]);
    setThemeName(selectedTheme);
  };

  return { theme, themeName, changeTheme };
};
