INSERT INTO public.users (user_name, user_email, user_password, role)
SELECT 'Author', 'author@mail.ru', '$2a$10$5v5Zz5z5z5z5z5z5z5z5u', 'ADMIN'
WHERE NOT EXISTS (
    SELECT 1 FROM public.users WHERE user_email = 'author@mail.ru'
);