import { Box, Button, TextField, Typography } from "@mui/material";
import Joi from "joi";
import { FormEvent } from "react";
import { useTranslation } from "react-i18next";

export const PurchaseSchema = Joi.object({
    name: Joi.string().trim().required(),
    quantity: Joi.number().required(),
    email: Joi.string().trim().required(),
    address: Joi.string().trim().required(),
});

type PurchaseFormProps = {
    title: string;
    name: string;
    setName: (name: string) => void;
    quantity: string;
    setQuantity: (quantity: string) => void;
    email: string;
    setEmail: (email: string) => void;
    address: string;
    setAddress: (address: string) => void;
    onSubmit: (e: FormEvent) => void;
    errors: {
        name?: string;
        quantity?: string;
        email?: string;
        address?: string;
    };
};

const PurchaseForm: React.FC<PurchaseFormProps> = ({
    title,
    name,
    setName,
    quantity,
    setQuantity,
    email,
    setEmail,
    address,
    setAddress,
    onSubmit,
    errors,
}) => {
    const { t } = useTranslation();
    return (
        <Box>
            <Box textAlign="center" sx={{ mt: 4, mb: 4 }}>
                <Typography variant="h4">{title}</Typography>
            </Box>

            <TextField
                fullWidth
                label= {t('form.name')}
                value={name}
                onChange={(e) => setName(e.target.value)}
                sx={{ mb: 3 }}
                required
                error={!!errors.name}
                helperText={errors.name}
            />

            <TextField
                fullWidth
                label={t('form.quantity')}
                value={quantity}
                onChange={(e) => setQuantity(e.target.value)}
                sx={{ mb: 3 }}
                required
                error={!!errors.quantity}
                helperText={errors.quantity}
            />

            <TextField
                fullWidth
                label={t('form.email')}
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                sx={{ mb: 3 }}
                required
                error={!!errors.email}
                helperText={errors.email}
            />

            <TextField
                fullWidth
                label={t('form.address')}
                value={address}
                onChange={(e) => setAddress(e.target.value)}
                sx={{ mb: 3 }}
                required
                error={!!errors.address}
                helperText={errors.address}
            />

            <Button variant="contained" onClick={onSubmit}>
            {t('save')}
            </Button>
        </Box>
    );
};

export default PurchaseForm;
