/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        'wave': {
          'dark': '#2c003e',
          'medium': '#7a1e78', 
          'accent': '#ff00cc',
          'light': '#ff66cc',
          'purple': '#4a1d7c',
          'deep': '#34034b'
        }
      },
      backgroundImage: {
        'wave-gradient': 'linear-gradient(135deg, #2c003e 0%, #7a1e78 50%, #ff00cc 100%)',
      }
    },
  },
  plugins: [],
}