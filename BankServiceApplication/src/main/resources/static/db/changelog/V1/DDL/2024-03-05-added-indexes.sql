create INDEX idx_user_emails_user_id on t_email_address (user_id);

create UNIQUE INDEX idx_user_emails on t_email_address (email);