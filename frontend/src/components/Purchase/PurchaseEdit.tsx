import { useNavigate, useParams } from 'react-router-dom';
import { CreatePurchase } from '../../models/Purchase';
import { useUpdatePurchase } from '../../mutation/purchase.mutation';
import { FormEvent, useCallback, useEffect, useState } from 'react';
import { Container } from '@mui/material';
import State from '../State';
import PurchaseForm, { PurchaseSchema } from './PurchaseForm';
import useErrors from '../../hooks/useErrors';
import { usePurchase } from '../../query/purchase.query';
import { useTranslation } from 'react-i18next';
import { useAlert } from '../../hooks/useAlert';

const PurchaseEdit: React.FC = () => {
  const { t } = useTranslation();
  const title = t('purchase.edit_purchase_title');
  const { id, purchaseId } = useParams();
  const idNumber = parseInt(id as string);
  const purchaseIdNumber = parseInt(purchaseId as string);
  const { mutate, isPending, isError, error, isSuccess } = useUpdatePurchase(idNumber, purchaseIdNumber);
  const {
    data: purchase,
    isLoading: purchaseIsLoading,
    isError: purchaseIsError,
    error: purchaseError,
  } = usePurchase(idNumber, purchaseIdNumber);
  const alert = useAlert();

  const { errors, setError } = useErrors();
  const [name, setName] = useState('');
  const [address, setAddress] = useState('');
  const [quantity, setQuantity] = useState('');
  const [email, setEmail] = useState('');

  useEffect(() => {
    if (purchase) {
      setName(purchase.name || '');
      setAddress(purchase.address || '');
      setQuantity(purchase.quantity.toString() || '');
      setEmail(purchase.email || '');
    }
  }, [purchase]);

  const navigate = useNavigate();

  const handleEdit = useCallback(
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
      alert(t('purchase.purchase_updated'), 'success');
      navigate(`/purchase/${id}`);
    }
  }, [alert, id, isSuccess, navigate, t]);

  return (
    <State
      isError={isError || purchaseIsError}
      isLoading={isPending || purchaseIsLoading}
      error={error || purchaseError}
    >
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
          onSubmit={handleEdit}
          errors={errors}
        />
      </Container>
    </State>
  );
};

export default PurchaseEdit;
