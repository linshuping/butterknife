package butterknife;

import butterknife.compiler.ButterKnifeProcessor;
import com.google.testing.compile.JavaFileObjects;
import javax.tools.JavaFileObject;
import org.junit.Test;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

public class OnTextChangedTest {
  @Test public void textChanged() {
    JavaFileObject source = JavaFileObjects.forSourceString("test.Test", ""
        + "package test;\n"
        + "import android.app.Activity;\n"
        + "import butterknife.OnTextChanged;\n"
        + "public class Test extends Activity {\n"
        + "  @OnTextChanged(1) void doStuff() {}\n"
        + "}"
    );

    JavaFileObject expectedSource = JavaFileObjects.forSourceString("test/Test$$ViewBinder", ""
        + "package test;\n"
        + "import android.text.Editable;\n"
        + "import android.text.TextWatcher;\n"
        + "import android.view.View;\n"
        + "import android.widget.TextView;\n"
        + "import butterknife.Unbinder;\n"
        + "import butterknife.internal.Finder;\n"
        + "import butterknife.internal.ViewBinder;\n"
        + "import java.lang.CharSequence;\n"
        + "import java.lang.IllegalStateException;\n"
        + "import java.lang.Object;\n"
        + "import java.lang.Override;\n"
        + "public class Test$$ViewBinder<T extends Test> implements ViewBinder<T> {\n"
        + "  @Override\n"
        + "  public Unbinder bind(Finder finder, T target, Object source) {\n"
        + "    InnerUnbinder unbinder = new InnerUnbinder(target);\n"
        + "    bindToTarget(target, finder, source, unbinder);\n"
        + "    return unbinder;\n"
        + "  }\n"
        + "  protected static void bindToTarget(final Test target, Finder finder, Object source, InnerUnbinder unbinder) {\n"
        + "    View view;\n"
        + "    view = finder.findRequiredView(source, 1, \"method 'doStuff'\");\n"
        + "    unbinder.view1 = view;\n"
        + "    unbinder.view1TextWatcher = new TextWatcher() {\n"
        + "      @Override\n"
        + "      public void onTextChanged(CharSequence p0, int p1, int p2, int p3) {\n"
        + "        target.doStuff();\n"
        + "      }\n"
        + "      @Override\n"
        + "      public void beforeTextChanged(CharSequence p0, int p1, int p2, int p3) {\n"
        + "      }\n"
        + "      @Override\n"
        + "      public void afterTextChanged(Editable p0) {\n"
        + "      }\n"
        + "    };\n"
        + "    ((TextView) view).addTextChangedListener(unbinder.view1TextWatcher);\n"
        + "  }\n"
        + "  protected static class InnerUnbinder<T extends Test> implements Unbinder {\n"
        + "    protected T target;\n"
        + "    View view1;\n"
        + "    TextWatcher view1TextWatcher;\n"
        + "    protected InnerUnbinder(T target) {\n"
        + "      this.target = target;\n"
        + "    }\n"
        + "    @Override\n"
        + "    public void unbind() {\n"
        + "      if (this.target == null) throw new IllegalStateException(\"Bindings already cleared.\");\n"
        + "      ((TextView) view1).removeTextChangedListener(view1TextWatcher);\n"
        + "      view1TextWatcher = null;\n"
        + "      view1 = null;\n"
        + "      this.target = null;\n"
        + "    }\n"
        + "  }\n"
        + "}"
    );

    assertAbout(javaSource()).that(source)
        .processedWith(new ButterKnifeProcessor())
        .compilesWithoutError()
        .and()
        .generatesSources(expectedSource);
  }
}
