package giaodienchuan.model.FrontEnd.FormQuanLy;

import giaodienchuan.model.BackEnd.QuanLyChiTietHoaDon.QuanLyChiTietHoaDonBUS;
import giaodienchuan.model.BackEnd.QuanLyHoaDon.QuanLyHoaDonBUS;
import giaodienchuan.model.FrontEnd.FormHienThi.HienThiHoaDon;
import giaodienchuan.model.FrontEnd.FormThemSua.ThemSuaHoaDonForm;
import giaodienchuan.model.FrontEnd.GiaoDienChuan.LoginForm;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;

public class QuanLyHoaDonForm extends JPanel {

    HienThiHoaDon formHienThi = new HienThiHoaDon();
    JButton btnThem = new JButton("Thêm");
    JButton btnSua = new JButton("Sửa");
    JButton btnXoa = new JButton("Xóa");

    public QuanLyHoaDonForm() {
        setLayout(new BorderLayout());

        // buttons
        btnThem.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_add_30px.png")));
        btnXoa.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_delete_forever_30px_1.png")));
        btnSua.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_support_30px.png")));
        
        if(!LoginForm.quyenLogin.getChiTietQuyen().contains("qlHoaDon")) {
            btnThem.setEnabled(false);
            btnXoa.setEnabled(false);
            btnSua.setEnabled(false);
        }

        JPanel plBtn = new JPanel();
        // Không cho phép thêm sửa xóa trong quản lý, chỉ trong bán hàng mới được
        plBtn.add(btnThem);
        plBtn.add(btnXoa);
        plBtn.add(btnSua);
        btnThem.setEnabled(false);
        btnXoa.setEnabled(false);
        btnSua.setEnabled(false);

        this.add(formHienThi, BorderLayout.CENTER);
        this.add(plBtn, BorderLayout.NORTH);

        // actionlistener
        btnThem.addActionListener((ActionEvent ae) -> {
            btnThemMouseClicked();
        });
        btnXoa.addActionListener((ActionEvent ae) -> {
            btnXoaMouseClicked();
        });
        btnSua.addActionListener((ActionEvent ae) -> {
            btnSuaMouseClicked();
        });
    }

    private void btnSuaMouseClicked() {
        String mahd = formHienThi.getSelectedHoaDon();
        if (mahd != null) {
            ThemSuaHoaDonForm tshd = new ThemSuaHoaDonForm("Sửa", mahd);
            tshd.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    formHienThi.refresh();
                }
            });
        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn hóa đơn nào để sửa");
        }
    }

    private void btnXoaMouseClicked() {
        String mahd = formHienThi.getSelectedHoaDon();
        if (mahd != null) {
            if (JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa hóa đơn " + mahd + 
                    " ? Mọi chi tiết trong hóa đơn sẽ bị xóa theo", 
                    "Chú ý", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
                
                new QuanLyChiTietHoaDonBUS().deleteAll(mahd);
                new QuanLyHoaDonBUS().delete(mahd);
                
                formHienThi.refresh();
            }

        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn sản phẩm nào để xóa");
        }
    }

    private void btnThemMouseClicked() {
        ThemSuaHoaDonForm themhd = new ThemSuaHoaDonForm("Thêm", "");
        themhd.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                formHienThi.refresh();
            }
        });
    }
}
