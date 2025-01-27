CREATE TABLE public.users (
                              user_id BIGSERIAL PRIMARY KEY,
                              user_name VARCHAR(30) NOT NULL,
                              user_email VARCHAR(30) NOT NULL UNIQUE,
                              user_password VARCHAR(256) NOT NULL,
                              role VARCHAR(50) NOT NULL
);

CREATE TABLE public.tasks (
                              task_id BIGSERIAL PRIMARY KEY,
                              header VARCHAR(50) NOT NULL,
                              description VARCHAR(255) NOT NULL,
                              status_id VARCHAR(50) NOT NULL,
                              priority_id VARCHAR(50) NOT NULL,
                              author_id BIGINT NOT NULL,
                              assignee_id BIGINT NOT NULL,
                              CONSTRAINT fk_author FOREIGN KEY (author_id) REFERENCES public.users(user_id),
                              CONSTRAINT fk_assignee FOREIGN KEY (assignee_id) REFERENCES public.users(user_id)
);

CREATE TABLE public.comments (
                                 comment_id BIGSERIAL PRIMARY KEY,
                                 comment_text TEXT NOT NULL,
                                 task_id BIGINT NOT NULL,
                                 user_id BIGINT NOT NULL,
                                 CONSTRAINT fk_task FOREIGN KEY (task_id) REFERENCES public.tasks(task_id),
                                 CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES public.users(user_id)
);