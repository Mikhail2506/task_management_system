INSERT INTO public.users (user_name, user_email, user_password, role)
SELECT 'Author', 'author@mail.ru', '$2a$10$vy1zonN0mwRgvN2irXrB0ufoDfnK5b280hmbGH3p1e2yl.yW2yx.6', 'ADMIN'
WHERE NOT EXISTS (
    SELECT 1 FROM public.users WHERE user_email = 'author@mail.ru'
);