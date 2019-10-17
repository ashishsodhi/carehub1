import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ITask } from 'app/shared/model/task.model';
import { getEntities as getTasks } from 'app/entities/task/task.reducer';
import { IDocument } from 'app/shared/model/document.model';
import { getEntities as getDocuments } from 'app/entities/document/document.reducer';
import { IRecipient } from 'app/shared/model/recipient.model';
import { getEntities as getRecipients } from 'app/entities/recipient/recipient.reducer';
import { IMessageItem } from 'app/shared/model/message-item.model';
import { getEntities as getMessageItems } from 'app/entities/message-item/message-item.reducer';
import { getEntity, updateEntity, createEntity, reset } from './recipient-item.reducer';
import { IRecipientItem } from 'app/shared/model/recipient-item.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IRecipientItemUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IRecipientItemUpdateState {
  isNew: boolean;
  taskId: string;
  documentId: string;
  recipientId: string;
  messageItemId: string;
}

export class RecipientItemUpdate extends React.Component<IRecipientItemUpdateProps, IRecipientItemUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      taskId: '0',
      documentId: '0',
      recipientId: '0',
      messageItemId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getTasks();
    this.props.getDocuments();
    this.props.getRecipients();
    this.props.getMessageItems();
  }

  saveEntity = (event, errors, values) => {
    values.whenCreated = convertDateTimeToServer(values.whenCreated);

    if (errors.length === 0) {
      const { recipientItemEntity } = this.props;
      const entity = {
        ...recipientItemEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/recipient-item');
  };

  render() {
    const { recipientItemEntity, tasks, documents, recipients, messageItems, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="carehubApp.recipientItem.home.createOrEditLabel">Create or edit a RecipientItem</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : recipientItemEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="recipient-item-id">ID</Label>
                    <AvInput id="recipient-item-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="permissionToAllLabel" check>
                    <AvInput id="recipient-item-permissionToAll" type="checkbox" className="form-control" name="permissionToAll" />
                    Permission To All
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="whenCreatedLabel" for="recipient-item-whenCreated">
                    When Created
                  </Label>
                  <AvInput
                    id="recipient-item-whenCreated"
                    type="datetime-local"
                    className="form-control"
                    name="whenCreated"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.recipientItemEntity.whenCreated)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="recipient-item-task">Task</Label>
                  <AvInput id="recipient-item-task" type="select" className="form-control" name="task.id">
                    <option value="" key="0" />
                    {tasks
                      ? tasks.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="recipient-item-document">Document</Label>
                  <AvInput id="recipient-item-document" type="select" className="form-control" name="document.id">
                    <option value="" key="0" />
                    {documents
                      ? documents.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="recipient-item-recipient">Recipient</Label>
                  <AvInput id="recipient-item-recipient" type="select" className="form-control" name="recipient.id">
                    <option value="" key="0" />
                    {recipients
                      ? recipients.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="recipient-item-messageItem">Message Item</Label>
                  <AvInput id="recipient-item-messageItem" type="select" className="form-control" name="messageItem.id">
                    <option value="" key="0" />
                    {messageItems
                      ? messageItems.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/recipient-item" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  tasks: storeState.task.entities,
  documents: storeState.document.entities,
  recipients: storeState.recipient.entities,
  messageItems: storeState.messageItem.entities,
  recipientItemEntity: storeState.recipientItem.entity,
  loading: storeState.recipientItem.loading,
  updating: storeState.recipientItem.updating,
  updateSuccess: storeState.recipientItem.updateSuccess
});

const mapDispatchToProps = {
  getTasks,
  getDocuments,
  getRecipients,
  getMessageItems,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(RecipientItemUpdate);
