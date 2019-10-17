import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IMessage } from 'app/shared/model/message.model';
import { getEntities as getMessages } from 'app/entities/message/message.reducer';
import { getEntity, updateEntity, createEntity, reset } from './message-item.reducer';
import { IMessageItem } from 'app/shared/model/message-item.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMessageItemUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IMessageItemUpdateState {
  isNew: boolean;
  messageId: string;
}

export class MessageItemUpdate extends React.Component<IMessageItemUpdateProps, IMessageItemUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      messageId: '0',
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

    this.props.getMessages();
  }

  saveEntity = (event, errors, values) => {
    values.whenCreated = convertDateTimeToServer(values.whenCreated);

    if (errors.length === 0) {
      const { messageItemEntity } = this.props;
      const entity = {
        ...messageItemEntity,
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
    this.props.history.push('/entity/message-item');
  };

  render() {
    const { messageItemEntity, messages, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="carehubApp.messageItem.home.createOrEditLabel">Create or edit a MessageItem</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : messageItemEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="message-item-id">ID</Label>
                    <AvInput id="message-item-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="whenCreatedLabel" for="message-item-whenCreated">
                    When Created
                  </Label>
                  <AvInput
                    id="message-item-whenCreated"
                    type="datetime-local"
                    className="form-control"
                    name="whenCreated"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.messageItemEntity.whenCreated)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="message-item-message">Message</Label>
                  <AvInput id="message-item-message" type="select" className="form-control" name="message.id">
                    <option value="" key="0" />
                    {messages
                      ? messages.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/message-item" replace color="info">
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
  messages: storeState.message.entities,
  messageItemEntity: storeState.messageItem.entity,
  loading: storeState.messageItem.loading,
  updating: storeState.messageItem.updating,
  updateSuccess: storeState.messageItem.updateSuccess
});

const mapDispatchToProps = {
  getMessages,
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
)(MessageItemUpdate);
