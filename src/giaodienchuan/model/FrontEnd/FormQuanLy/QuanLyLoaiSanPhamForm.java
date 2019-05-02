package giaodienchuan.model.FrontEnd.FormQuanLy;

import giaodienchuan.model.BackEnd.QuanLyLoaiSanPham.QuanLyLoaiSanPhamBUS;
import giaodienchuan.model.BackEnd.WorkWithExcel.XuatExcel;
import giaodienchuan.model.FrontEnd.FormHienThi.HienThiLoaiSanPham;
import giaodienchuan.model.FrontEnd.FormThemSua.ThemSuaLoaiSanPhamForm;
import giaodienchuan.model.FrontEnd.GiaoDienChuan.LoginForm;
import giaodienchuan.model.FrontEnd.MyButton.ExportExcelButton;
import giaodienchuan.model.FrontEnd.MyButton.SuaButton;
import giaodienchuan.model.FrontEnd.MyButton.ThemButton;
import giaodienchuan.model.FrontEnd.MyButton.XoaButton;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class QuanLyLoaiSanPhamForm extends JPanel {

    HienThiLoaiSanPham formHienThi = new HienThiLoaiSanPham();

    ThemButton btnThem = new ThemButton();
    SuaButton btnSua = new SuaButton();
    XoaButton btnXoa = new XoaButton();
    ExportExcelButton btnXuatExcel = new ExportExcelButton();

    JComboBox<String> cbTypeSearch;

    // index
    final int MALSP_I = 1, TENLSP_I = 2, MOTA_I = 3;

    public QuanLyLoaiSanPhamForm() {
        setLayout(new BorderLayout());

        // buttons        
        if(!LoginForm.quyenLogin.getChiTietQuyen().contains("qlLoaiSanPham")) {
            btnThem.setEnabled(false);
            btnXoa.setEnabled(false);
            btnSua.setEnabled(false);
        }

        JPanel plBtn = new JPanel();
        plBtn.add(btnThem);
        plBtn.add(btnXoa);
        plBtn.add(btnSua);
        plBtn.add(btnXuatExcel);

        //=========== add all to this jpanel ===========
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
        btnXuatExcel.addActionListener((ActionEvent ae) -> {
            try {
                new XuatExcel().xuatFileExcelLoaiSanPham();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Lỗi khi xuất file excel!" + e.getMessage());
            }
        });
    }

    private void btnSuaMouseClicked() {
        String malsp = formHienThi.getSelectedRow(1);
        if (malsp != null) {
            ThemSuaLoaiSanPhamForm sualsp = new ThemSuaLoaiSanPhamForm("Sửa", malsp);

            // https://stackoverflow.com/questions/4154780/jframe-catch-dispose-event
            sualsp.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    formHienThi.refresh();
                }
            });

        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn loại sản phẩm nào để sửa");
        }
    }

    private void btnXoaMouseClicked() {
        String malsp = formHienThi.getSelectedRow(1);
        if (malsp != null) {
            if (JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa loại sản phẩm " + malsp + " ?", "Chú ý", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
                new QuanLyLoaiSanPhamBUS().delete(malsp);
                formHienThi.refresh();
            }

        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn loại sản phẩm nào để xóa");
        }
    }

    private void btnThemMouseClicked() {
        ThemSuaLoaiSanPhamForm themlsp = new ThemSuaLoaiSanPhamForm("Thêm", "");

        themlsp.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                formHienThi.refresh();
            }
        });
    }
}
