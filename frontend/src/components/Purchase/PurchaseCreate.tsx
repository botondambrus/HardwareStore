import { useNavigate, useParams } from 'react-router-dom';
import useErrors from '../../hooks/useErrors';
import { useCreatePurchase } from '../../mutation/purchase.mutation';
import { FormEvent, useCallback, useEffect, useState } from 'react';
import PurchaseForm, { PurchaseSchema } from './PurchaseForm';
import { CreatePurchase } from '../../models/Purchase';
import { Container } from '@mui/material';
import State from '../State';
import { useTranslation } from 'react-i18next';
import { useAlert } from '../../hooks/useAlert';

const PurchaseCreate: React.FC = () => {
  const { t } = useTranslation();
  const title = t('purchase.create_purchase_title');
  const { id } = useParams();
  const idNumber = parseInt(id as string);
  const { mutate, isPending, isError, error, isSuccess } = useCreatePurchase(idNumber);
  const navigate = useNavigate();
  const alert = useAlert();

  const { errors, setError } = useErrors();
  const [name, setName] = useState('');
  const [address, setAddress] = useState('');
  const [quantity, setQuantity] = useState('');
  const [email, setEmail] = useState('');

  const handleCreate = useCallback(
    (e: FormEvent) => {
      e.preventDefault();

      const editedPurchase: CreatePurchase = {
        name: name,
        address: address,
        quantity: parseFloat(quantity),
        email: email,
      };

      const { error } = PurchaseSchema.validate(editedPurchase);
      if (error) {
        error.details.forEach((detail) => {
          const fieldName = typeof detail.path[0] === 'number' ? detail.path[0].toString() : detail.path[0];
          setError(fieldName, detail.message);
        });
      } else {
        mutate(editedPurchase);
      }
    },
    [name, address, quantity, email, setError, mutate],
  );

  useEffect(() => {
    if (isSuccess) {
      alert(t('purchase.purchase_created'), 'success');
      navigate(`/purchase/${id}`);
    }
  }, [alert, id, isSuccess, navigate, t]);

  return (
    <State isError={isError} isLoading={isPending} error={error}>
      <Container maxWidth="md">
        <PurchaseForm
          title={title}
          name={name}
          setName={setName}
          quantity={quantity}
          setQuantity={setQuantity}
          email={email}
          setEmail={setEmail}
          address={address}
          setAddress={setAddress}
          onSubmit={handleCreate}
          errors={errors}
        />
      </Container>
    </State>
  );
};

export default PurchaseCreate;
