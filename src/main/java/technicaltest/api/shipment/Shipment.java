package technicaltest.api.shipment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import technicaltest.api.Order.Order;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="shipments")
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="shipment_id", nullable = false)
    private UUID uuid;
    @Column(name="shipment_date", nullable = false)
    private Date shipmentDate;
    @Column(name="method", nullable = false)
    private UUID method;
    @OneToOne(mappedBy = "shipment")
    private Order order;

    public Shipment(UUID uuid, Date shipmentDate, UUID method) {
        this.uuid = uuid;
        this.shipmentDate = shipmentDate;
        this.method = method;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Date getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(Date shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public UUID getMethod() {
        return method;
    }

    public void setMethod(UUID method) {
        this.method = method;
    }
}
