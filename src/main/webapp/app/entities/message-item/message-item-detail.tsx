import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './message-item.reducer';
import { IMessageItem } from 'app/shared/model/message-item.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMessageItemDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class MessageItemDetail extends React.Component<IMessageItemDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { messageItemEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            MessageItem [<b>{messageItemEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="whenCreated">When Created</span>
            </dt>
            <dd>
              <TextFormat value={messageItemEntity.whenCreated} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>Message</dt>
            <dd>{messageItemEntity.message ? messageItemEntity.message.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/message-item" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/message-item/${messageItemEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ messageItem }: IRootState) => ({
  messageItemEntity: messageItem.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MessageItemDetail);
